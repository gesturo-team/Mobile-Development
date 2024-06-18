package com.example.myapplication.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.QuizQuestionsItem
import com.example.myapplication.databinding.ItemQuizPageBinding
import java.util.Locale

class QuizDetailAdapter(var questionsItem: List<QuizQuestionsItem>) : RecyclerView.Adapter<QuizDetailAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemQuizPageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val radioGroup: RadioGroup = binding.btnAnswer
        fun bind(questionsItem: QuizQuestionsItem){
            binding.apply {
                Glide.with(ivQuiz.context)
                    .load(questionsItem.urlImage)
                    .into(ivQuiz)

                radioGroup.removeAllViews()
                questionsItem.answers?.forEachIndexed { _, alphabetAnswersItem ->
                    val radioButton = RadioButton(root.context).apply {
                        id = View.generateViewId()
                        text = alphabetAnswersItem?.value?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                        isChecked = alphabetAnswersItem?.value == questionsItem.userAnswer
                    }
                    radioGroup.addView(radioButton)
                }

                radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    val radioButton: RadioButton? = radioGroup.findViewById(checkedId)
                    if (radioButton != null) {
                        questionsItem.userAnswer = radioButton.text.toString()
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuizPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = questionsItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(questionsItem[position])
    }

    override fun onViewRecycled(holder: MyViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.btnAnswer.setOnCheckedChangeListener(null)
    }
}