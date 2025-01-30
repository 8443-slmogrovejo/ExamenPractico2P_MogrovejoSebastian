package com.banquito.branch.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@Document(collection = "branch_holidays")
public class BranchHoliday {
    
    @Id
    private String idHoliday;
    
    @DocumentReference
    private Branch idBranch;
    
    private LocalDate date;
    private String name;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idHoliday == null) ? 0 : idHoliday.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BranchHoliday other = (BranchHoliday) obj;
        if (idHoliday == null) {
            if (other.idHoliday != null)
                return false;
        } else if (!idHoliday.equals(other.idHoliday))
            return false;
        return true;
    }
}
