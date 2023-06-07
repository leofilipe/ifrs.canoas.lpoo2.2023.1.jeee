package br.edu.ifrs.canoas.jee.webapp.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<ID> {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;
 
    public ID getId() {
        return id;
    }
 
    public void setId(ID id) {
        this.id = id;
    }
 
}
