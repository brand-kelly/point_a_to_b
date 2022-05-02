package adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
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
        val app: ImageButton = view.findViewById(R.id.app)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.serviceName.text = item.serviceName.toString()
        holder.time.text = item.time.toString() + "mins"
        holder.price.text = "$" + item.price.toString()
        if (item.app == 0) {
            holder.app.visibility = View.GONE
        }else{
            holder.app.setBackgroundResource(item.app)
            holder.app.setOnClickListener {
                openApp(item.url)
            }
        }
    }

    override fun getItemCount() = dataset.size
    fun openApp(url: String){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        context.startActivity(openURL)
    }
}