import java.util.Scanner;

public class One {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Circle Circle = new Circle(10);

        System.out.print("반지름을 입력하세요 : ");
        Circle.radius = sc.nextInt();
        System.out.print("원의 넓이는 : " + Circle.calArea());




    }

    //원클래스
    static class Circle{
        static int radius; // 반지름
        final double PI = 3.141592653589793; // π =3.141592653589793XXXX

        public Circle(int radius){ // 클래스 Circle의 생성자
            this.radius = radius;
        }

        public static double getRadius() {
            return radius;
        }

        public static void setRadius(int radius) {
            Circle.radius = radius;
        }

        //원의 넓이를 구하는 로직
        public double calArea(){
            return Math.pow(radius,2)*PI;
// == radius * radius * PI;
        }
    }
}
