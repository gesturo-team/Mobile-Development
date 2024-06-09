package com.example.myapplication.ui.quiz.alphabet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.AlphabetQuestionsItem
import com.example.myapplication.databinding.ItemQuizPageBinding

class QuizAlphabetAdapter(private val questionsItem: List<AlphabetQuestionsItem>) : RecyclerView.Adapter<QuizAlphabetAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: ItemQuizPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(questionsItem: AlphabetQuestionsItem){
            binding.apply {
                Glide.with(ivQuiz.context)
                    .load(questionsItem.urlImage)
                    .into(ivQuiz)
                btnAnswer1.text = questionsItem.answers?.getOrNull(0)?.value
                btnAnswer2.text = questionsItem.answers?.getOrNull(1)?.value
                btnAnswer3.text = questionsItem.answers?.getOrNull(2)?.value
                btnAnswer4.text = questionsItem.answers?.getOrNull(3)?.value

                //selected answer
                when (questionsItem.selectedAnswer) {
                    0 -> btnAnswer1.isChecked = true
                    1 -> btnAnswer2.isChecked = true
                    2 -> btnAnswer3.isChecked = true
                    3 -> btnAnswer4.isChecked = true
                    else -> binding.btnAnswer.clearCheck()
                }

                binding.btnAnswer.setOnCheckedChangeListener { _, checkedId ->
                    questionsItem.selectedAnswer= when (checkedId) {
                        R.id.btnAnswer1 -> 0
                        R.id.btnAnswer2 -> 1
                        R.id.btnAnswer3 -> 2
                        R.id.btnAnswer4 -> 3
                        else -> -1
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuizPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(questionsItem[position])
    }
}