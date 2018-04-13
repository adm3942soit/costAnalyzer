package com.adonis.costAnalyzer.data.persons;

import com.adonis.costAnalyzer.data.Audit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oksdud on 10.04.2017.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "address", schema = "")
@Getter
@Setter
@NoArgsConstructor
@Cacheable(value = false)
public class Address extends Audit {
        @Column(name = "STREET", nullable = false)
        private String street;
        @Column(name = "ZIP", nullable = false)
        private String zip;
        @Column(name = "CITY", nullable = false)
        private String city;
        @Column(name = "COUNTRY", nullable = false)
        private String country;
}
