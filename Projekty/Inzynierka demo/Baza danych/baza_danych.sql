-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 13 Sty 2016, 22:05
-- Wersja serwera: 5.1.73-cll
-- Wersja PHP: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `id_proj` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `date_open` date NOT NULL,
  `date_start` date NOT NULL,
  `date_end` date NOT NULL,
  PRIMARY KEY (`id_proj`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Zrzut danych tabeli `project`
--

INSERT INTO `project` (`id_proj`, `title`, `date_open`, `date_start`, `date_end`) VALUES
(1, 'Projekt pierwszy "Testowy"', '2015-11-16', '2015-11-17', '2015-11-30'),
(2, 'Projekt zagraniczny', '2015-11-16', '2015-11-17', '2015-11-20'),
(3, 'Badania Naukowe 2015 IT', '2015-11-16', '2015-11-16', '2015-11-30'),
(4, 'Badania naukowe 2016 IT', '2016-01-01', '2016-11-01', '2016-11-30'),
(5, 'Aplikacja internetowa', '2015-11-16', '2015-11-30', '2015-12-01'),
(6, 'Testy jednostkowe', '2015-11-21', '2015-11-22', '2015-12-30'),
(14, 'Proc', '2016-01-10', '2016-01-12', '2016-01-22'),
(15, 'Pierwszy_tomaszew', '2016-01-10', '2016-01-18', '2016-01-25'),
(16, 'dfgdfg', '2016-01-12', '2016-01-02', '2016-01-09'),
(17, 'fghfgh', '2016-01-12', '2016-01-08', '2016-01-09'),
(18, 'Piotr', '2016-01-13', '2016-01-04', '2016-01-31');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `proj_user`
--

CREATE TABLE IF NOT EXISTS `proj_user` (
  `id_proj` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `id_type` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Zrzut danych tabeli `proj_user`
--

INSERT INTO `proj_user` (`id_proj`, `id`, `id_type`) VALUES
(1, 10, 2),
(15, 10, 1),
(14, 1, 1),
(1, 2, 2),
(6, 3, 1),
(5, 2, 1),
(4, 1, 1),
(3, 1, 1),
(2, 1, 1),
(1, 1, 1),
(1, 3, 2),
(16, 10, 1),
(1, 17, 3),
(17, 10, 1),
(18, 18, 1),
(18, 10, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj1`
--

CREATE TABLE IF NOT EXISTS `tasks_proj1` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=86 ;

--
-- Zrzut danych tabeli `tasks_proj1`
--

INSERT INTO `tasks_proj1` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(19, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 0, 'zadanie 2bcdi', 'opis 2222', '2015-12-02', '2015-12-08', 10, 1, 1, 1, NULL, 1),
(14, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 0, 'irt', 'Ala ma kotarr', '2016-01-14', '2017-01-20', 10, NULL, NULL, NULL, NULL, NULL),
(6, 5, 'zadanie 4_1', 'opis  4_1', '2015-12-08', '2015-12-09', 10, 1, 1, 1, 'No bl nie działa', 1),
(64, 5, 'Piotr', '1212', '0000-00-00', '2016-01-20', 10, 1, 1, 1, 'Popraw!', 1),
(17, 5, 'Zadanie 2.2', 'Opis zadania 2.2', '2016-01-27', '2016-01-30', 10, 1, 1, 1, NULL, 1),
(11, 5, 'Zadanie 2.3', '', '0000-00-00', '0000-00-00', 10, 1, 1, 1, NULL, 1),
(79, 75, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(80, 78, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(81, 5, '', '', '2016-01-05', '2016-01-07', 10, 1, 1, 1, NULL, 1),
(82, 64, '', '', '0000-00-00', '0000-00-00', 10, 1, NULL, NULL, NULL, NULL),
(85, 5, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(29, 0, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(84, 5, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(83, 84, 'zadanie 4_1', 'opis  4_1', '0000-00-00', '2015-12-09', 0, NULL, NULL, NULL, NULL, NULL),
(78, 5, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(75, 78, 'zadanie 4_1', 'opis  4_1', '0000-00-00', '2015-12-09', 0, NULL, NULL, NULL, NULL, NULL),
(77, 75, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(76, 5, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj2`
--

CREATE TABLE IF NOT EXISTS `tasks_proj2` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Zrzut danych tabeli `tasks_proj2`
--

INSERT INTO `tasks_proj2` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, 0, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, 1, NULL, 1),
(2, 1, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(3, 2, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj3`
--

CREATE TABLE IF NOT EXISTS `tasks_proj3` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `tasks_proj3`
--

INSERT INTO `tasks_proj3` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(2, 0, 'zadanie 2bcd', 'opis 2222', '2015-12-02', '2015-12-08', 2, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj4`
--

CREATE TABLE IF NOT EXISTS `tasks_proj4` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `tasks_proj4`
--

INSERT INTO `tasks_proj4` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(4, 0, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL),
(3, 0, '', '', '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj5`
--

CREATE TABLE IF NOT EXISTS `tasks_proj5` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `tasks_proj5`
--

INSERT INTO `tasks_proj5` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(2, 0, 'zadanie 2bcd', 'opis 2222', '2015-12-02', '2015-12-08', 2, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj6`
--

CREATE TABLE IF NOT EXISTS `tasks_proj6` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `data_start` date DEFAULT NULL,
  `data_stop` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `tasks_proj6`
--

INSERT INTO `tasks_proj6` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(2, 0, 'zadanie 2bcd', 'opis 2222', '2015-12-02', '2015-12-08', 2, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj14`
--

CREATE TABLE IF NOT EXISTS `tasks_proj14` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `description` text,
  `data_start` date NOT NULL,
  `data_stop` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Zrzut danych tabeli `tasks_proj14`
--

INSERT INTO `tasks_proj14` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, NULL, '', NULL, '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj15`
--

CREATE TABLE IF NOT EXISTS `tasks_proj15` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `description` text,
  `data_start` date NOT NULL,
  `data_stop` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Zrzut danych tabeli `tasks_proj15`
--

INSERT INTO `tasks_proj15` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, NULL, '', NULL, '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj16`
--

CREATE TABLE IF NOT EXISTS `tasks_proj16` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `description` text,
  `data_start` date NOT NULL,
  `data_stop` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Zrzut danych tabeli `tasks_proj16`
--

INSERT INTO `tasks_proj16` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, NULL, '', NULL, '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj17`
--

CREATE TABLE IF NOT EXISTS `tasks_proj17` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `description` text,
  `data_start` date NOT NULL,
  `data_stop` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Zrzut danych tabeli `tasks_proj17`
--

INSERT INTO `tasks_proj17` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, NULL, '', NULL, '0000-00-00', '0000-00-00', 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tasks_proj18`
--

CREATE TABLE IF NOT EXISTS `tasks_proj18` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dependence` int(11) DEFAULT NULL,
  `title` varchar(500) NOT NULL,
  `description` text,
  `data_start` date NOT NULL,
  `data_stop` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `iscorrect` tinyint(1) DEFAULT NULL,
  `correctdescript` text,
  `END` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Zrzut danych tabeli `tasks_proj18`
--

INSERT INTO `tasks_proj18` (`id`, `dependence`, `title`, `description`, `data_start`, `data_stop`, `user_id`, `done`, `checked`, `iscorrect`, `correctdescript`, `END`) VALUES
(1, 0, 'Zadanie 1', 'dsfsdf', '2016-01-06', '2016-01-08', 10, 1, 1, NULL, NULL, NULL),
(2, 1, 'sdfsdf', 'sdwe3re34', '2016-01-05', '2016-01-22', 10, 1, 1, NULL, NULL, NULL),
(3, 2, 'werwer', '3wser3', '2016-01-28', '2016-01-31', 10, 1, 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `type`
--

CREATE TABLE IF NOT EXISTS `type` (
  `id_type` int(11) NOT NULL AUTO_INCREMENT,
  `type` text NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Zrzut danych tabeli `type`
--

INSERT INTO `type` (`id_type`, `type`) VALUES
(1, 'właściciel'),
(2, 'pracownik'),
(3, 'klient');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` int(11) NOT NULL,
  `address` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `status`, `name`, `surname`, `email`, `telephone`, `address`) VALUES
(1, 'bob@o2.pl', '9f9d51bc70ef21ca5c14f307980a29d8', 'Programista Java firmy Motorola', 'Piotr Adam', 'Tomaszewskii', 'bob@o2.pl', 123123123, 'Motorola5'),
(2, 'piotr@o2.pl', '99fdb06613cd9b8f328b6cadd98b1c23', 'Manager firmy Motorola Solution', 'Piotr', 'Piotrowski', 'piotr@o2.pl', 123456789, NULL),
(3, 'admin@o2.pl', '21232f297a57a5a743894a0e4a801fc3', 'Admin', 'Admin', 'Adminowy', 'admin@o2.pl', 987654321, NULL),
(10, 'piotr@tomaszew.com', '99fdb06613cd9b8f328b6cadd98b1c23', 'Proc', 'Piotr Adam (pracownik)', 'Tomaszewski', 'piotr@tomaszew.com', 123321123, ''),
(18, 'demon17@o2.pl', 'fb94810e037239126e1fd83c697b4619', 'Pracownik Politechniki Krakowskiej', 'Dzisiaj ', 'Dzisiejszy ', 'demon17@o2.pl', 889555926, ''),
(17, 'piotrekha15@gmail.com', 'fb94810e037239126e1fd83c697b4619', 'Nowy pracownik', 'Anonimowy', 'Anonim', 'piotrekha15@gmail.com', 0, '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
