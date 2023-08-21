package gr.aueb.cf.schoolappsoa.model;

import java.util.Date;

public class Meeting {
    private Long id;
    private Student student;
    private Teacher teacher;
    private String meetingRoom;
    private Date meetingDate;

    public Meeting() {
    }

    public Meeting(Long id, Student student, Teacher teacher, String meetingRoom, Date meetingDate) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.meetingRoom = meetingRoom;
        this.meetingDate = meetingDate;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
}
