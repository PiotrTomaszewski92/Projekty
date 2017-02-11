#!/usr/bin/python
# -*- coding: utf-8 -*-
from collections import Counter
import json
import os
import shutil
import subprocess
import sys
from datetime import datetime
import glob
import re
import resource
import glob
import signal

if len(sys.argv) < 4:
    print """
    Uruchamianie: %s <src_dir> <unpack_dir> <zad1|zad2|zad3> [zad1|zad2|zad3] ...

        src_dir - katalog gdzie znajduje się spakowane archiwum tgz
        unpack_dir - folder do którego zostanie wypakowane archiwum i tam odbędzie się kompilacja i testy
        zad<n> - numer laboratorium do przetestowania

    Przykład:

        /home/shared/prir/manage.py ~/src ~/unpack zad1 zad2

    """ % sys.argv[0]
    exit()

SRC_PATH = sys.argv[1]
DST_PATH = sys.argv[2]
LABS = sys.argv[3:]

students = set([])
for f in os.listdir(SRC_PATH):
    rx = re.search('^([a-zA-Z]+(-[a-zA-Z]+)?)-zad', f)
    if rx:
        students.add(rx.groups()[0])

class PrirException(Exception):
    pass

class Alarm(Exception):
    pass

def alarm_handler(signum, frame):
    raise Alarm
signal.signal(signal.SIGALRM, alarm_handler)

def setlimits():
    resource.setrlimit(resource.RLIMIT_CPU, (200, 200))


class Tester:
    def __init__(self, name):
        self.name = name
        self.p = None
        self.stats = {}

    def green(self, text):
        print '\033[1;32m%s\033[0m' % text

    def red(self, text):
        print '\033[1;31m%s\033[0m' % text

    def yellow(self, text):
        print '\033[1;32m%s\033[0m' % text

    def white(self, text):
        print '\033[0m%s' % text

    def blue(self, text):
        print '\033[1;34m%s\033[0m' % text

    def run(self, labs):
        print '\n\n'
        self.green('='*100)
        self.green('\t%s' % student)
        self.green('='*100)

        for lab in labs:
            try:
                self.unpack(lab)
                getattr(self, lab)()
            except PrirException, e:
                self.red(e)

    def zad1(self):
        self.green('-'*100 + '\n\tZadanie 1\n' + '-'*100)
        self.checkdir('zad1')
        self.checkfile('dok.tex')
        self.compile('macierz_omp')
        self.checkrun(['./macierz_omp'])
        self.checkrun(['./macierz_omp', '1', '-10'])
        self.checkrun(['./macierz_omp', '1', '10'])
        self.checkrun(['./macierz_omp', '1', '100'])
        self.checkrun(['./macierz_omp', '8', '100'])
        self.checkrun(['./macierz_omp', '1', '10000'])
        self.checkrun(['./macierz_omp', '8', '10000'])

    def zad2(self):
        self.green('-'*100 + '\n\tZadanie 2\n' + '-'*100)
        self.checkdir('zad2')
        self.checkfile('dok.tex')
        self.compile('macierz_mpi')
        self.checkrun(['mpirun', '-n', '1', './macierz_mpi'])
        self.checkrun(['mpirun', '-n', '1', './macierz_mpi', '-10'])
        self.checkrun(['mpirun', '-n', '1', './macierz_mpi', '10'])
        self.checkrun(['mpirun', '-n', '1', './macierz_mpi', '100'])
        self.checkrun(['mpirun', '-n', '8', './macierz_mpi', '100'])
        self.checkrun(['mpirun', '-n', '1', './macierz_mpi', '10000'])
        self.checkrun(['mpirun', '-n', '4', './macierz_mpi', '10000'])
        self.checkrun(['mpirun', '-n', '8', './macierz_mpi', '10000'])

    def zad3(self):
        self.green('-'*100 + '\n\tZadanie 3\n' + '-'*100)
        self.checkdir('zad3')
        self.checkfile('dok.tex')
        self.compile('macierz_gpu')
        self.checkrun(['./macierz_gpu'])
        self.checkrun(['./macierz_gpu', '32'])
        self.checkrun(['./macierz_gpu', '128'])
        self.checkrun(['./macierz_gpu', '1024'])
        self.checkrun(['./macierz_gpu', '2048'])
        self.checkrun(['./macierz_gpu', '4096'])
        self.checkrun(['./macierz_gpu', '8192'])

    def zad4(self):
        self.green('-'*100 + '\n\tZadanie 4\n' + '-'*100)
        self.checkdir('zad4')
        self.checkfile('dok.tex')
        self.compile('gauss_omp')
        self.checkrun(['./gauss_omp'])
        self.checkrun(['./gauss_omp', '1', '/home/shared/prir/gauss/not_found.jpg', 'not_found.jpg'])
        self.checkrun(['./gauss_omp', '1', '/home/shared/prir/gauss/f2.jpg', '1.jpg'])
	self.checkrun(['./gauss_omp', '1', '/home/shared/prir/gauss/f2.jpg', '1.jpg'])
	self.checkrun(['./gauss_omp', '1', '/home/shared/prir/gauss/f2.jpg', '1.jpg'])

	self.checkrun(['./gauss_omp', '2', '/home/shared/prir/gauss/f2.jpg', '2.jpg'])
	self.checkrun(['./gauss_omp', '2', '/home/shared/prir/gauss/f2.jpg', '2.jpg'])
	self.checkrun(['./gauss_omp', '2', '/home/shared/prir/gauss/f2.jpg', '2.jpg'])

	self.checkrun(['./gauss_omp', '3', '/home/shared/prir/gauss/f2.jpg', '3.jpg'])
	self.checkrun(['./gauss_omp', '3', '/home/shared/prir/gauss/f2.jpg', '3.jpg'])
	self.checkrun(['./gauss_omp', '3', '/home/shared/prir/gauss/f2.jpg', '3.jpg'])

	self.checkrun(['./gauss_omp', '4', '/home/shared/prir/gauss/f2.jpg', '4.jpg'])
	self.checkrun(['./gauss_omp', '4', '/home/shared/prir/gauss/f2.jpg', '4.jpg'])
	self.checkrun(['./gauss_omp', '4', '/home/shared/prir/gauss/f2.jpg', '4.jpg'])

	self.checkrun(['./gauss_omp', '5', '/home/shared/prir/gauss/f2.jpg', '5.jpg'])
	self.checkrun(['./gauss_omp', '5', '/home/shared/prir/gauss/f2.jpg', '5.jpg'])
	self.checkrun(['./gauss_omp', '5', '/home/shared/prir/gauss/f2.jpg', '5.jpg'])

	self.checkrun(['./gauss_omp', '6', '/home/shared/prir/gauss/f2.jpg', '6.jpg'])
	self.checkrun(['./gauss_omp', '6', '/home/shared/prir/gauss/f2.jpg', '6.jpg'])
	self.checkrun(['./gauss_omp', '6', '/home/shared/prir/gauss/f2.jpg', '6.jpg'])

	self.checkrun(['./gauss_omp', '7', '/home/shared/prir/gauss/f2.jpg', '7.jpg'])
	self.checkrun(['./gauss_omp', '7', '/home/shared/prir/gauss/f2.jpg', '7.jpg'])
	self.checkrun(['./gauss_omp', '7', '/home/shared/prir/gauss/f2.jpg', '7.jpg'])

	self.checkrun(['./gauss_omp', '8', '/home/shared/prir/gauss/f2.jpg', '8.jpg'])
	self.checkrun(['./gauss_omp', '8', '/home/shared/prir/gauss/f2.jpg', '8.jpg'])
	self.checkrun(['./gauss_omp', '8', '/home/shared/prir/gauss/f2.jpg', '8.jpg'])

	self.checkrun(['./gauss_omp', '9', '/home/shared/prir/gauss/f2.jpg', '9.jpg'])
	self.checkrun(['./gauss_omp', '9', '/home/shared/prir/gauss/f2.jpg', '9.jpg'])
	self.checkrun(['./gauss_omp', '9', '/home/shared/prir/gauss/f2.jpg', '9.jpg'])

	self.checkrun(['./gauss_omp', '10', '/home/shared/prir/gauss/f2.jpg', '10.jpg'])
	self.checkrun(['./gauss_omp', '10', '/home/shared/prir/gauss/f2.jpg', '10.jpg'])
	self.checkrun(['./gauss_omp', '10', '/home/shared/prir/gauss/f2.jpg', '10.jpg'])

    def zad5(self):
        self.green('-'*100 + '\n\tZadanie 5\n' + '-'*100)
        self.checkdir('zad5')
        self.checkfile('dok.tex')
        self.compile('gauss_mpi')
        self.checkrun(['mpirun', '-n', '1', './gauss_mpi'])
        self.checkrun(['mpirun', '-n', '1', './gauss_mpi', '/home/shared/prir/gauss/not_found.jpg', 'not_found.jpg'])
        self.checkrun(['mpirun', '-n', '1', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '1.jpg'])
        self.checkrun(['mpirun', '-n', '2', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
        self.checkrun(['mpirun', '-n', '3', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '3.jpg'])
        self.checkrun(['mpirun', '-n', '4', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '4.jpg'])
        self.checkrun(['mpirun', '-n', '5', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '5.jpg'])
        self.checkrun(['mpirun', '-n', '6', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '6.jpg'])
	self.checkrun(['mpirun', '-n', '7', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '7.jpg'])
        self.checkrun(['mpirun', '-n', '8', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '8.jpg'])
        self.checkrun(['mpirun', '-n', '9', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '9.jpg'])
        self.checkrun(['mpirun', '-n', '10', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '10.jpg'])
        self.checkrun(['mpirun', '-n', '11', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '11.jpg'])
        self.checkrun(['mpirun', '-n', '12', './gauss_mpi', '/home/shared/prir/gauss/f1.jpg', '12.jpg'])

    def zad6(self):
        self.green('-'*100 + '\n\tZadanie 6\n' + '-'*100)
        self.checkdir('zad6')
        self.checkfile('dok.tex')
        self.compile('gauss_gpu')
        #self.checkrun(['./gauss_gpu'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/not_found.jpg', 'not_found.jpg'])
        self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
	self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
	self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
	self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
	self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f1.jpg', '2.jpg'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f2.jpg', '4.jpg'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/f3.jpg', '6.jpg'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/1.jpg', '8.jpg'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/2.jpg', '10.jpg'])
        #self.checkrun(['./gauss_gpu', '/home/shared/prir/gauss/3.jpg', '12.jpg'])

    def unpack(self, lab):
        self.blue("Wypakowywanie %s" % lab)
        archive = '%s-%s.tgz' % (self.name, lab)
        archive2 = '%s-%s.tar.gz' % (self.name, lab)
        p = os.path.join(SRC_PATH, archive)
        if not os.path.isfile(p):
            p = os.path.join(SRC_PATH, archive2)
        if not os.path.isfile(p):
            raise PrirException("Archiwum %s/%s nie istnieje" % (archive, archive2))
        try:
            self.call(['tar', 'zxf', p, '-C', DST_PATH])
        except:
            raise PrirException('Nie można wypakować archiwum')

    def call(self, *args, **kw):
        kw['preexec_fn'] = setlimits
        timeout = kw.pop('timeout', 20)
        env = dict(os.environ)
        env['LD_LIBRARY_PATH'] = '/usr/local/cuda/lib64:/usr/local/cuda/lib:/usr/local/lib'
        p = subprocess.Popen(*args, stdout=subprocess.PIPE, stderr=subprocess.PIPE, env=env, **kw)

        try:
            signal.alarm(timeout)
            stdoutdata, stderrdata = p.communicate()
            signal.alarm(0)
            if p.returncode != 0:
                self.red("Niepoprawny kod wyjścia: %d" % p.returncode)
            if stdoutdata:
                self.white('Stdout: \n--------\n%s\n--------' % stdoutdata)
            if stderrdata:
                self.red('Stderr: \n--------\n%s\n--------' % stderrdata)
        except Alarm:
            p.kill()
            self.red("Zbyt długi czas wykonania")

    def checkdir(self, zad):
        self.p = os.path.join(DST_PATH, self.name, zad)
        if not os.path.isdir(self.p):
            raise PrirException("Folder %s nie istnieje" % self.p)

    def checkfile(self, f):
        p = os.path.join(self.p, f)
        if not os.path.isfile(p):
            raise PrirException("Plik %s nie istnieje" % p)

    def compile(self, prog):
        try:
            os.remove(os.path.join(self.p, prog))
        except:
            pass
        if glob.glob(os.path.join(self.p, '*.pro')):
            try:
                self.call(['qmake'], cwd=self.p)
            except:
                pass
        elif glob.glob(os.path.join(self.p, 'CMakeLists.txt')):
            try:
                self.call(['cmake', '.'], cwd=self.p)
            except:
                pass
        try:
            self.call(['make'], cwd=self.p, timeout=100)
        except:
            raise PrirException('Nie można skompilować programu')
        self.checkfile(prog)
        try:
            p = subprocess.Popen(['pdflatex', 'dok.tex'], cwd=self.p, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            p.communicate(input='')
            if p.returncode != 0:
                raise PrirException('Nie można skompilować dokumentacji')
        except:
            raise PrirException('Nie można skompilować dokumentacji')
        self.checkfile('dok.pdf')

    def checkrun(self, params):
        try:
            self.blue('\nUruchomienie: %s' % ' '.join(params))
            now = datetime.now()
            self.call(params, cwd=self.p, timeout=30)
            self.blue('Czas wykonania: %s' % (datetime.now() - now))
        except Exception, e:
            self.red("Błąd uruchomienia: %s" % e)

for student in students:
    t = Tester(student)
    t.run(LABS)
