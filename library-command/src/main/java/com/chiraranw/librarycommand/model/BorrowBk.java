package com.chiraranw.librarycommand.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "borrows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bkId;
    private String mbId;

}
