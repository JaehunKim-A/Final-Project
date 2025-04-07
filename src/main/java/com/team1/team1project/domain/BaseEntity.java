package com.team1.team1project.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
public class BaseEntity {
	@CreatedDate
	@Column(name = "reg_date", updatable = false)
	public LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "mod_date")
	public LocalDateTime modDate;
}