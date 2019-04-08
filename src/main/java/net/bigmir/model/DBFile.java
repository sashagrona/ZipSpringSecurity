package net.bigmir.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;

@Entity
@NoArgsConstructor
@Data
@Table(name = "files")
public class DBFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "size_kB")
    private Double size;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "zip_id")
    private Zip zip;

    public DBFile(String name,Double size, Zip zip) {
        this.name = name;
        this.zip = zip;
        this.size=size;
    }


}
