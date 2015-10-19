package com.bizislife.core.hibernate.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("passwordreset")
public class PasswordResetToken extends UserToken{

}
