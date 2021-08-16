package com.marlonncarvalhosa.covidmap.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel

class SecondSymptomAdapter(
    private val onSymtomSelectedListener : (SecondSymptomModel) -> Unit,
    private val onSymptomDesselectedListener: (SecondSymptomModel) -> Unit)
    : RecyclerView.Adapter<SecondSymptomAdapter.ViewHolder>() {

    private var secondSymptomModel: List<SecondSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding
    private val holders : MutableList<SecondSymptomAdapter.ViewHolder> = ArrayList()

    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(secondSymptomModel: SecondSymptomModel, position: Int) {
            binding.materialCheckBox.text = secondSymptomModel.secondSymptomName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecondSymptomAdapter.ViewHolder, position: Int) {
        holders.add(holder)
        val secondSymptomModel = secondSymptomModel[position]
        holder.bind(secondSymptomModel, position)
        if (secondSymptomModel.secondSymptomName == "Nenhum desses sintomas") { binding.materialCheckBox.tag = "REMOVEALL" }

        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.apply {
                when(tag) {
                    "REMOVEALL" -> onRemoveAllSelected(this)
                    else -> checkStatus(isChecked, secondSymptomModel)
                }
            }
        }
    }

    private fun onRemoveAllSelected(compoundButton: CompoundButton?) {
        holders.forEach { it.itemView.findViewById<MaterialCheckBox>(R.id.materialCheckBox).isChecked = false }
    }

    private fun checkStatus(checked: Boolean, secondSymptomModel: SecondSymptomModel) {
        when(checked) {
            true -> onSymtomSelectedListener(secondSymptomModel)
            false -> onSymptomDesselectedListener(secondSymptomModel)
        }

    }



    override fun getItemCount(): Int = secondSymptomModel.size


    fun updateSymptom(secondSymptomModel: List<SecondSymptomModel>) {
        this.secondSymptomModel = secondSymptomModel
        notifyDataSetChanged()
    }

}