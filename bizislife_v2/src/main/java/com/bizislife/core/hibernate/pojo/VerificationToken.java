package com.bizislife.core.hibernate.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("verification")
public class VerificationToken extends UserToken{

}
