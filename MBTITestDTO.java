package Shw1013_MBTI;

// MBTITestDTO 클래스는 MBTI 테스트 결과를 저장하는 데이터 전송 객체(Data Transfer Object)입니다.
public class MBTITestDTO {
    private int id; // 데이터베이스에서의 고유 식별자
    private String name; // 사용자의 이름
    private String phone; // 사용자의 전화번호
    private String mbti; // 사용자의 MBTI 유형

    // 생성자: ID 없이 객체를 생성 (새로운 결과를 추가할 때 사용)
    public MBTITestDTO(String name, String phone, String mbti) {
        this.name = name;
        this.phone = phone;
        this.mbti = mbti;
    }

    // 생성자: ID와 함께 객체를 생성 (데이터베이스에서 조회할 때 사용)
    public MBTITestDTO(int id, String name, String phone, String mbti) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mbti = mbti;
    }

    // ID를 반환하는 getter 메소드
    public int getId() {
        return id;
    }

    // 이름을 반환하는 getter 메소드
    public String getName() {
        return name;
    }

    // 전화번호를 반환하는 getter 메소드
    public String getPhone() {
        return phone;
    }

    // MBTI 유형을 반환하는 getter 메소드
    public String getMbti() {
        return mbti;
    }
}
