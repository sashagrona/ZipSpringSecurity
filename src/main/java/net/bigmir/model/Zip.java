package net.bigmir.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bigmir.model.DBFile;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Zip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "zip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<DBFile> files = new LinkedList<>();
    @Column(name = "size_kB")
    private Double size;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private SimpleUser user;


    public Zip(String name) {
        this.name = name;
    }

    public void addFiles(List<DBFile> list) {
        this.files = list;
    }

    public void addUser(SimpleUser user) {
        this.user = user;
    }

    public void addSize(Double size) {
        this.size = size;
    }



}
