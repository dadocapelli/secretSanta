package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "restricao_sorteio")
public class RestricaoSorteio implements Serializable {

    private static final long serialVersionUID = 7261940910658403992L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_participante_from")
    private Participante participanteFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_participante_to")
    private Participante participanteTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_restricao")
    private TipoRestricao tipoRestricao = TipoRestricao.AMBOS_NAO_SE_TIRAM;

    public Participante getParticipanteFrom() {
        return participanteFrom;
    }

    public void setParticipanteFrom(Participante participanteFrom) {
        this.participanteFrom = participanteFrom;
    }

    public Participante getParticipanteTo() {
        return participanteTo;
    }

    public void setParticipanteTo(Participante participanteTo) {
        this.participanteTo = participanteTo;
    }

    public TipoRestricao getTipoRestricao() {
        return tipoRestricao;
    }

    public void setTipoRestricao(TipoRestricao tipoRestricao) {
        this.tipoRestricao = tipoRestricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
