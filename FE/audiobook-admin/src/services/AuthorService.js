import axios from "axios"
const REST_API_BASE_URL_AUTHOR = "http://localhost:8080/api/authors";
// get all authors
export const listAuthors = () => axios.get(REST_API_BASE_URL_AUTHOR);
// add a author
export const addAuthor = (author) => axios.post(REST_API_BASE_URL_AUTHOR, author);
// get by id
export const getAuthor = (authorId) => axios.get(REST_API_BASE_URL_AUTHOR + '/' + authorId);
// update
export const updateAuthor = (authorId, author) => axios.put(REST_API_BASE_URL_AUTHOR + '/' + authorId, author);
// delete
export const deleteAuthor = (authorId) => axios.delete(REST_API_BASE_URL_AUTHOR + '/' + authorId);