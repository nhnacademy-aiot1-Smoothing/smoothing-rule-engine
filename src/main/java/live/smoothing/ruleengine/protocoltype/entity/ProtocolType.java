package live.smoothing.ruleengine.protocoltype.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@Table(name = "protocol_types")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProtocolType {

    @Id
    @Column(name = "protocol_type")
    private String protocolType;

}
