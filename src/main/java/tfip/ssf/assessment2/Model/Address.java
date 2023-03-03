package tfip.ssf.assessment2.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Address {
    
    @NotNull(message = "Mandatory field")
    @NotEmpty(message="Please state your name")
    @Size(min = 2,message = "Please put a longer name")
    private String name;

    @NotNull(message = "Mandatory field")
    @NotEmpty(message="Please state your address")
    private String address;

    //Constructors
    public Address(@NotNull(message = "Mandatory field") String name,
            @NotNull(message = "Mandatory field") @NotEmpty(message = "Please state your address") String address) {
        this.name = name;
        this.address = address;
    }

    public Address() {
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
