package Shw1013_MBTI;

public class MBTITestDTO {
    private int id;
    private String name;
    private String phone;
    private String mbti;

    public MBTITestDTO(String name, String phone, String mbti) {
        this.name = name;
        this.phone = phone;
        this.mbti = mbti;
    }

    public MBTITestDTO(int id, String name, String phone, String mbti) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mbti = mbti;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMbti() {
        return mbti;
    }
}
