package dsm.api.pi.Enum;

public enum UserRoles {
    UNIDADE("unidade"),
    ADMIN("admin");


    private String role;

    UserRoles(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
