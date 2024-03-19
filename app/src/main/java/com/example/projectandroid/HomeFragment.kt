import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectandroid.BoardData
import com.example.projectandroid.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //파이어베이스 업로드 예시
        binding.sampleBtn.setOnClickListener{
            setDocument(
                BoardData(
                    name = "김길환",
                    title = "예시입니다.",
                    content = "안녕하세요, 첫 업로드입니다."
                )
            )

        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //파이어베이스 데이터 업로드를 위한 코드
    private fun setDocument(data: BoardData) {
        FirebaseFirestore.getInstance()
            .collection("boardDatas")
            .document(data.name)
            .set(data)
            .addOnSuccessListener {
                binding.textView.text = "success!"
            }
            .addOnFailureListener {
                binding.textView.text = "fail!"
            }
    }
}
