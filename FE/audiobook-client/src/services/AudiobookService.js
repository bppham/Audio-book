import axios from "axios"
const REST_API_BASE_URL_AUDIOBOOK = "http://localhost:8080/api/audiobooks";
// get all categories
export const listAudioBook = () => axios.get(REST_API_BASE_URL_AUDIOBOOK);
// add a category
export const addAudioBook = async (formData) => {
    try {
      const response = await axios.post(REST_API_BASE_URL_AUDIOBOOK, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
};
// get by id
export const getAudiobookById = (id) => axios.get(REST_API_BASE_URL_AUDIOBOOK + '/' + id);
// update
export const updateAudiobook = async (id, audioBook, imageFile, audioFiles) => {
  const formData = new FormData();

  // Chuyển đổi audioBook thành JSON string
  const audioBookJson = JSON.stringify({
      id: audioBook.id,
      title: audioBook.title,
      author: audioBook.author,
      voice: audioBook.voice,
      description: audioBook.description,
      note: audioBook.note,
      categories: audioBook.categories.map(id => ({ id })), // Gửi danh sách ID
  });

  formData.append("audioBook", audioBookJson);

  // Nếu có ảnh mới, thêm vào formData
  if (imageFile) {
      formData.append("imageFile", imageFile);
  }

  // Nếu có file audio mới, thêm vào formData
  if (audioFiles && audioFiles.length > 0) {
      audioFiles.forEach((file) => {
          formData.append("audioFiles", file);
      });
  }

  return axios.put(`${REST_API_BASE_URL_AUDIOBOOK}/${id}`, formData, {
      headers: {
          "Content-Type": "multipart/form-data",
      },
  });
};
// delete
export const deleteAudiobook = (id) => axios.delete(REST_API_BASE_URL_AUDIOBOOK + '/' + id);