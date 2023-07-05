package com.transport.model;

import com.transport.model.enums.LoadApproach;
import com.transport.model.enums.LoadMethod;
import com.transport.model.enums.Packaging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LoadApproach loadApproach;

    @Column(name = "loading_method")
    @Enumerated(EnumType.STRING)
    private LoadMethod loadMethod;

    @Enumerated(EnumType.STRING)
    private Packaging packaging;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private Size size;

    @OneToMany(mappedBy = "cargo")
    private List<Transportation> transportations;
}
