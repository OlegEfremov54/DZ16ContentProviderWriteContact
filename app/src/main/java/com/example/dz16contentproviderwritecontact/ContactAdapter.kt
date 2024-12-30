package com.example.dz16contentproviderwritecontact

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val context: Context, private val listener: ContactClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    val contacts = ArrayList<Contact>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<Contact>) {
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTV: TextView = itemView.findViewById(R.id.nameTV)
        private val itemSurnameTV: TextView = itemView.findViewById(R.id.surnameTV)
        private val itemPhoneTV: TextView = itemView.findViewById(R.id.phoneTV)
        private val itemTimeStamp: TextView = itemView.findViewById(R.id.timeTV)
        val itemIconDeleteIV: ImageView = itemView.findViewById(R.id.iconDeleteIV)


        fun bind(contact: Contact) {
            itemNameTV.text = contact.name
            itemSurnameTV.text = contact.surname
            itemPhoneTV.text = contact.phone
            itemTimeStamp.text = contact.timeStamp


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewHolder =
            ContactViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.contact_item, parent, false))
        viewHolder.itemIconDeleteIV.setOnClickListener {
            listener.onItemClicked(contacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.bind(currentContact)
    }



//    class ContactComparator : DiffUtil.ItemCallback<Contact>() {
//        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }

    interface ContactClickListener {
        fun onItemClicked(contact: Contact)
    }
}
