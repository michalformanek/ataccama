package cz.mformanek.ataccama.tenant.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @Column(columnDefinition = "varchar(255)")
    String name;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String hostname;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String port;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String databaseName;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String username;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String password; //TODO: Sanitize output

}