
package pizzaria.java.modelo;

import pizzaria.java.exception.PizzariaException;

public class Gerente {
    private int ID;
    private String User;
    private String Password;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void setID(String ID) throws PizzariaException {
    	try {
    	setID(Integer.parseInt(ID));
    	}catch(NumberFormatException e) {
    		throw new PizzariaException("Formato inválido");
    	}
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Passoword) {
        this.Password = Passoword;
    }
    
    
}
