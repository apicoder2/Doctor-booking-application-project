package com.practo.practo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "experience", nullable = false)
    private int experience;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "hospital", nullable = false)
    private String hospital;

    @Column(name = "description")
    private String description;



    public Doctor(String qualification) {
        this.qualification = qualification;
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                ", hospital='" + hospital + '\'' +
                ", description='" + description + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }

}
