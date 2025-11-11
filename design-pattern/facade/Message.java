package facade;

public class Message {
    private Row row;

    public Message(Row row) {
        this.row = row;
    }

    public String makeName(){
        return "Name: " + row.getName();
    }

    public String makeBirthday(){
        return "Name: " + row.getBirthday();
    }

    public String makeEmail(){
        return "Name: " + row.getEmail();
    }

}
