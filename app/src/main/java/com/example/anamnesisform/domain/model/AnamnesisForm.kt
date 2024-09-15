package com.example.anamnesisform.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AnamnesisForm(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "nome") val nome: String?,
    @ColumnInfo(name = "data_nascimento") val dataNascimento: String?,
    @ColumnInfo(name = "profissao") val profissao: String?,
    @ColumnInfo(name = "celular") val celular: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "blefarite") val blefarite: Boolean?,
    @ColumnInfo(name = "tercolCalazio") val tercolCalazio: Boolean?,
    @ColumnInfo(name = "conjutivite") val conjutivite: Boolean?,
    @ColumnInfo(name = "diabetes") val diabetes: Boolean?,
    @ColumnInfo(name = "glaucoma") val glaucoma: Boolean?,
    @ColumnInfo(name = "fioAFio") val fioAFio: Boolean?,
    @ColumnInfo(name = "volumeRusso") val volumeRusso: Boolean?,
    @ColumnInfo(name = "hibrido") val hibrido: Boolean?,
    @ColumnInfo(name = "megaVolume") val megaVolume: Boolean?,
    @ColumnInfo(name = "volumeBrasileiro") val volumeBrasileiro: Boolean?,
    @ColumnInfo(name = "express") val express: Boolean?,
    @ColumnInfo(name = "usaLentesContato") val usaLentesContato: Boolean?,
    @ColumnInfo(name = "gestante") val gestante: Boolean?,
    @ColumnInfo(name = "fumante") val fumante: Boolean?,
    @ColumnInfo(name = "lacrimeja_constante") val lacrimejaConstante: Boolean?,
    @ColumnInfo(name = "sensibilidade_luz") val sensibilidadeLuz: Boolean?,
    @ColumnInfo(name = "fazTratamentoOcular") val fazTratamentoOcular: Boolean?,
    @ColumnInfo(name = "fezCirurgiaOcularUltimos6Meses") val fezCirurgiaOcularUltimos6Meses: Boolean?,
    @ColumnInfo(name = "alergiaCosmeticosMaquiagens") val alergiaCosmeticosMaquiagens: Boolean?,
    @ColumnInfo(name = "alergiaProdutosHigienePessoal") val alergiaProdutosHigienePessoal: Boolean?,
    @ColumnInfo(name = "alergiaCosmetico") val alergiaCosmetico: String?,
    @ColumnInfo(name = "alergiaProdutosHigiene") val alergiaProdutosHigiene: String?,
    @ColumnInfo(name = "tratamentoOcular") val tratamentoOcular: String?
) : Serializable
