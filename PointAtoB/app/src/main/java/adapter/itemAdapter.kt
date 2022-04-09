package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pointatob.R
import model.Service
import org.w3c.dom.Text

class ItemAdapter (
    private val context: Context,
    private val dataset: List<Service>
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val serviceName: TextView = view.findViewById(R.id.service_name)
        val time: TextView = view.findViewById(R.id.time)
        val price: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.serviceName.text = context.resources.getString(item.serviceName)
        holder.time.text = context.resources.getInteger(item.time).toString() + "mins"
        holder.price.text = "$" + context.resources.getInteger(item.price).toString()
    }

    override fun getItemCount() = dataset.size
}