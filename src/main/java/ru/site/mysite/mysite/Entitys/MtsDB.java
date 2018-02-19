package ru.site.mysite.mysite.Entitys;


import javax.persistence.*;

@Entity
@Table(name = "mts")
public class MtsDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    public Long id;
    @Column(name="name")
    public String name;
    @Lob
    @Column(name="image", length = 10000000)
    public String img;

    public MtsDB(String _name, String _img){
        this.name = _name;
        this.img = _img;
    }

    public MtsDB(){}
}
