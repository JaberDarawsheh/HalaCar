package car.accessories;

public abstract class User {
    String email ,PhoneNumber,UserName,password;
    User(){}
    User(String email,String Password){
        this.email=email;
        this.password=Password;
    }

}
