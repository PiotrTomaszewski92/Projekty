
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Procislaw
 */
public class Graham {
    //int n = punkty().size();
    int n = 8;
    Point2d[] tangens = new Point2d[n-1];
    Point2d[] points = new Point2d[n];
    List<Integer> lista = new ArrayList<Integer>();
    
    int pocz=0;
    int[] stos = new int[n];
    
        
        
        int det(int a,int b,int c){
            double p=points[a].getX()*points[b].getY()+points[b].getX()*points[c].getY()+points[c].getX()*points[a].getY()-points[c].getX()*points[b].getY()-points[a].getX()*points[c].getY()-points[b].getX()*points[a].getY();
            if (p>=0) 
                return 1;
        return 0;
        }
        
        void  push(int nr){
            stos[pocz]=nr;
            lista.add(nr);
            pocz++;
        }
 
        //zdejmuje wartosc ze stosu
        void pops(){
           lista.remove(lista.size()-1);
            pocz--;
            
        }
    
    void graham()
    {
        double sY=points[0].getY(),sX=points[0].getX();
        push(0);
       // stosik.add(pocz++);
        for(int i = tangens.length-1; i >=0;i--){
                int nr=(int) tangens[i].getX();
                if(pocz==1) push(nr);
                else{
                     while(det(lista.get(pocz-2),lista.get(pocz-1),nr)==0 && pocz!=1) pops();
                    //while(det(stos[pocz-2],stos[pocz-1],nr)==0 && pocz!=1) pops();
                    push(nr);
                }//else
               // System.out.println("POCZ: "+pocz);
        }//for
       while(det(lista.get(pocz-2),lista.get(pocz-1),0)==0 && pocz!=1)pops();
        //while(det(stos[pocz-2],stos[pocz-1],0)==0 && pocz!=1)pops();
        if (pocz!=n)
            push(0);
    }
    
    
    public void przygotowanie (List<Point> arr){
        //int n = arr.size();
       float z=0;
        int i=0;
       
         for (Point xyzt : arr){
//             System.out.println(xyzt.getX()+" - "+xyzt.getY());
            z+=xyzt.getZ();
             points[i] = new Point2d(xyzt.getX(), xyzt.getY());
             i++;
         }
        Arrays.sort(points,new Komparator());
        
//        for (i=0; i<points.length;i++){
//             System.out.println(points[i].getX()+" - "+points[i].getY());            
//         }

//     System.out.println("===============================================");  
        float sX=points[0].getX(),sY=points[0].getY();
        tangens[0] = new Point2d();
//        System.out.println("sX="+points[0].getX()+",sY="+points[0].getY());
        for(i = 1; i < n;i++){
            float a=points[i].getX()-sX;
            float b=points[i].getY()-sY;
            float w=a/b;
//            System.out.println("i:"+i+" || a="+a+", b="+b+", w="+w);
            tangens[i-1]=new Point2d(i,w);   
//            System.out.println("tangens["+i+"]=("+tangens[i-1].getX()+", "+tangens[i-1].getY()+");");
        }
        
//         System.out.println("===============================================");  
         Arrays.sort(tangens,new Komparator());
//        for (i=0; i<tangens.length;i++){
//             System.out.println("Tangens["+i+"]"+tangens[i].getX()+" - "+tangens[i].getY());
//            
//         }
        
//        int ktory=1;
//        for( i = ((tangens.length)-1);i>=0;i--){
//            int p= (int)tangens[i].getX();
//            System.out.println((ktory++)+" : ("+points[p].getX()+ ","+points[p].getY()+")");
//        }
//        System.out.println("==========GRAHAM=====================================");  
        graham();
//        System.out.println("===========oTOCZKA=stos==================================="); 
        double pole=0;
        for(i = 0;i<lista.size();i++ ){
//            System.out.println(lista.get(i)+" : ("+points[lista.get(i)].getX()+","+points[lista.get(i)].getY()+")");
            
          
                
            if (i==0){
                pole+=(points[lista.get(i)].getX())*(points[lista.get(i+1)].getY()-points[lista.get(lista.size()-1)].getY());
            }else if (i==lista.size()-1){
                pole+=(points[lista.get(i)].getX())*(points[lista.get(0)].getY()-points[lista.get(i-1)].getY());
            }else{
                pole+=(points[lista.get(i)].getX())*(points[lista.get(i+1)].getY()-points[lista.get(i-1)].getY());
            }
//          System.out.println("Po iteracji "+(i+1)+" Pole: "+pole);      
            
        }
//        System.out.println("===========POLE po zsumowaniu: =="+pole+"=================================");
        pole = (Math.abs(pole))/2;
//        System.out.println("===========POLE: =="+pole+"===Srednia: "+z+"/"+n+"="+(z/n)+"=============================");  
//       System.out.println("===========Objetosc: =="+pole*(z/n)+"================================");  
       
        
//       System.out.println("===========Stosik=================================");  
//       System.out.println(stosik.pop());
//        System.out.println(stosik.pop());
//         System.out.println(stosik.pop());
//          System.out.println(stosik.pop());
//           for(Integer iii : lista){
//               System.out.println(iii);
//           }
           
       
    }
    
    
    
    
    private List<Point2d> punkty (){
        List<Point2d> arr2 = new ArrayList<Point2d>();
//// punkt a
//arr2.add(new Point2d(1,1));
//// punkt b
//arr2.add(new Point2d(6,2));
//// punkt c
//arr2.add(new Point2d(3,2));
//// punkt d
//arr2.add(new Point2d(4,3));
//// punkt e
//arr2.add(new Point2d(5,6));
//// punkt f
//arr2.add(new Point2d(3,5));
//// punkt g
//arr2.add(new Point2d(4,8));
//// punkt h
//arr2.add(new Point2d(2,6));
//// punkt i
//arr2.add(new Point2d(1,7));
//// punkt j
//arr2.add(new Point2d(0,4));

arr2.add(new Point2d(6,4));
arr2.add(new Point2d(3,4));
arr2.add(new Point2d(8,1));
arr2.add(new Point2d(1,1));


return arr2;
}

    
    private class Komparator implements Comparator<Point2d>{

        @Override
        public int compare(Point2d o1, Point2d o2) {
            Float y1 = ((Point2d) o1).getY();
            Float y2 = ((Point2d) o2).getY();
            int sComp = y1.compareTo(y2);
            
            if(sComp!=0){
                return sComp;
            } else{
                Float x1 = ((Point2d) o1).getX();
                Float x2 = ((Point2d) o2).getX();
                return x1.compareTo(x2);
            }
        }
        
    }
   
    
    
    public class Point2d {
        float x;
        float y;
        
        public Point2d(){}
        
        public Point2d (float x, float y){
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

    }
    
}
