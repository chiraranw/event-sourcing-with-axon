package com.chiraranw.librarycommand.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mbId;
    private String bkId;
}
