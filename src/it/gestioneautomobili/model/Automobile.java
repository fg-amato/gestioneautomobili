package it.gestioneautomobili.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "automobile")
public class Automobile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "marca")
	private String marca;
	@Column(name = "modello")
	private String modello;
	@Column(name = "targa")
	private String targa;
	@Column(name = "annoimmatricolazione")
	private int annoImmatricolazione;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "proprietario_id")
	private Proprietario proprietario;

	public Automobile() {
		super();
	}

	public Automobile(String marca, String modello, String targa, int annoImmatricolazione) {
		super();
		this.marca = marca;
		this.modello = modello;
		this.targa = targa;
		this.annoImmatricolazione = annoImmatricolazione;
	}

	public Automobile(String marca, String modello, String targa, int annoImmatricolazione,
			Proprietario proprietario) {
		super();
		this.marca = marca;
		this.modello = modello;
		this.targa = targa;
		this.annoImmatricolazione = annoImmatricolazione;
		this.proprietario = proprietario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public int getAnnoDiImmatricolazione() {
		return annoImmatricolazione;
	}

	public void setAnnoDiImmatricolazione(int annoImmatricolazione) {
		this.annoImmatricolazione = annoImmatricolazione;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	@Override
	public String toString() {
		return "Automobile [id=" + id + ", marca=" + marca + ", modello=" + modello + ", targa=" + targa
				+ ", annoImmatricolazione=" + annoImmatricolazione + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoImmatricolazione, id, marca, modello, proprietario, targa);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Automobile))
			return false;

		Automobile other = (Automobile) obj;
		return this.annoImmatricolazione == other.getAnnoDiImmatricolazione() && this.id.equals(other.getId())
				&& this.marca.equals(other.getMarca()) && this.modello.equals(other.getModello())
				&& this.proprietario.equals(other.getProprietario()) && this.targa.equals(other.getTarga());
	}

}
