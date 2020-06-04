import java.io.Serializable;

public class Technology implements Serializable {
    private static final long serialVersionUID = -556637077679195752L;
    private String subject;
    private String person;
    private String address;
    private String time;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "subject='" + subject + '\'' +
                ", person='" + person + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
