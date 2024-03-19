/*
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
                    name = "도로시",
                    title = "예시입니다.",
                    content = "안녕하세요, 첫 업로드입니다."
                )
            )
        }

        // Inflate the layout for this fragment
        return view
    }

    //파이어베이스 데이터 업로드를 위한 코드
    private fun setDocument(data: BoardData) {
        FirebaseFirestore.getInstance()
            .collection("boardDatas")
            .document(data.name)//중복처리, 기본키 설정 같은 것 (나중에 숫자나 id값으로 처리해서 사용할 예정)
            .set(data)
            .addOnSuccessListener {
                binding.textView.text = "success!"
            }
            .addOnFailureListener {
                binding.textView.text = "fail!"
            }

    }

    //자동호출
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectandroid.BoardData
import com.example.projectandroid.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val boardDataList = mutableListOf<BoardData>()
    private lateinit var adapter: BoardDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView
        adapter = BoardDataAdapter(boardDataList)
        binding.FBoardData.layoutManager = LinearLayoutManager(requireContext())
        binding.FBoardData.adapter = adapter

        // Retrieve data from Firestore
        retrieveData()

        // Inflate the layout for this fragment
        return view
    }

    // Retrieve data from Firestore
    private fun retrieveData() {
        FirebaseFirestore.getInstance()
            .collection("boardDatas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.id
                    val title = document.getString("title") ?: ""
                    val content = document.getString("content") ?: ""
                    val boardData = BoardData(name, title, content)
                    boardDataList.add(boardData)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    // Automatically called
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

