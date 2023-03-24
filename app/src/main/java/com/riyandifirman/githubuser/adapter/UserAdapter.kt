package com.riyandifirman.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
    private val listUser = ArrayList<User>()

    // fungsi untuk menambahkan data ke dalam list
    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    // fungsi untuk set on click listener pada item recyclerview
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // class untuk menampung view yang akan digunakan
    inner class ListViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        // fungsi untuk mengisi data ke dalam view
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(ivProfile)

                tvNama.text = user.login

            }
        }
    }

    // fungsi untuk membuat view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    // fungsi untuk mengembalikan jumlah data yang ada
    override fun getItemCount(): Int = listUser.size

    // fungsi untuk mengisi data ke dalam view holder
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    // interface untuk menangani event click pada item recyclerview
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}