/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacasino;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author danie
 */
@Entity
@Table(name = "apuesta")
@NamedQueries({
    @NamedQuery(name = "Apuesta.findAll", query = "SELECT a FROM Apuesta a"),
    @NamedQuery(name = "Apuesta.findById", query = "SELECT a FROM Apuesta a WHERE a.id = :id"),
    @NamedQuery(name = "Apuesta.findByMontoApostado", query = "SELECT a FROM Apuesta a WHERE a.montoApostado = :montoApostado"),
    @NamedQuery(name = "Apuesta.findByFecha", query = "SELECT a FROM Apuesta a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Apuesta.findByResultado", query = "SELECT a FROM Apuesta a WHERE a.resultado = :resultado")})
public class Apuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto_apostado")
    private BigDecimal montoApostado;
    @Basic(optional = false)
    @Column(name = "fecha")
    private LocalDateTime fecha;
    @Basic(optional = false)
    @Column(name = "resultado")
    private String resultado;
    @JoinColumn(name = "juego_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Juego juegoId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Apuesta() {
    }

    public Apuesta(Integer id) {
        this.id = id;
    }

    public Apuesta(Integer id, BigDecimal montoApostado, LocalDateTime fecha, String resultado) {
        this.id = id;
        this.montoApostado = montoApostado;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoApostado() {
        return montoApostado;
    }

    public void setMontoApostado(BigDecimal montoApostado) {
        this.montoApostado = montoApostado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Juego getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(Juego juegoId) {
        this.juegoId = juegoId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apuesta)) {
            return false;
        }
        Apuesta other = (Apuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpacasino.Apuesta[ id=" + id + " ]";
    }
    
}
