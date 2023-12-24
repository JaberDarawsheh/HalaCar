package car.accessories;

public abstract class User {
    String email;
    String password;
    User(){}
    User(String email,String password){
        this.email=email;
        this.password=password;
    }

}
