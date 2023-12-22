package car.accessories;

public abstract class User {
    String email;
    String phoneNumber;
    String userName;
    String password;
    User(){}
    User(String email,String password){
        this.email=email;
        this.password=password;
    }

}
