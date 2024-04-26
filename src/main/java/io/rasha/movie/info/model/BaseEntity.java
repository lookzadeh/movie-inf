package io.rasha.movie.info.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity implements Cloneable {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id = UUID.randomUUID();

  @Version protected Integer version;

  @Builder.Default protected Boolean deleted = false;

  @CreatedDate
  @Column(updatable = false)
  protected LocalDateTime createdDate;

  @Column @LastModifiedDate protected LocalDateTime lastModifiedDate;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @Column @LastModifiedBy private String modifiedBy;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
