package boardReview;

public class test {

	public static void main(String[] args) {
		person aPerson = new person();
		System.out.println(aPerson.age);
	}
}
class person{
	int age;
	
	person(){
		this.age = 20;
	}
}