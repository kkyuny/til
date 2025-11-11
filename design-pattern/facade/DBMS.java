package facade;

import java.util.HashMap;

public class DBMS {
    private HashMap<String, Row> db = new HashMap<>();

    public DBMS() {
        db.put("1", new Row("11", "1991", "a@a.com"));
        db.put("2", new Row("12", "1992", "b@a.com"));
        db.put("3", new Row("13", "1993", "c@a.com"));
    }

    public Row query(String name){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return db.get(name.toLowerCase());
    }
}
