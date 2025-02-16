import axios from "axios"
const REST_API_BASE_URL_EMPLOYEE = "http://localhost:8080/api/employees";
const REST_API_BASE_URL_ROLES = "http://localhost:8080/api/roles"
// get all employees
export const listEmployee = () => axios.get(REST_API_BASE_URL_EMPLOYEE);
// delete
export const deleteEmployee = (id) => axios.delete(REST_API_BASE_URL_EMPLOYEE + '/' + id);
// get all roles
export const listRole = () => axios.get(REST_API_BASE_URL_ROLES);
// add employee
export const addEmployee = async (formData) => {
    try {
      const response = await axios.post(REST_API_BASE_URL_EMPLOYEE, formData, {
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
export const getEmployeeById = (id) => axios.get(REST_API_BASE_URL_EMPLOYEE + '/' + id);
// update an employee
export const updateEmployee = async (id, employee, imageFile) => {
  const formData = new FormData();

  // Chuyển đổi audioBook thành JSON string
  const employeeJSON = JSON.stringify({
      name: employee.name,
      email: employee.email,
      phoneNumber: employee.phoneNumber,
      roles: employee.roles.map(role => role), // Gửi danh sách ID
  });
  console.log("employeeJSON : " + employeeJSON);

  formData.append("employee", employeeJSON);

  // Nếu có ảnh mới, thêm vào formData
  if (imageFile) {
      formData.append("avatars", imageFile);
  }

  return axios.put(`${REST_API_BASE_URL_EMPLOYEE}/${id}`, formData, {
      headers: {
          "Content-Type": "multipart/form-data",
      },
  });
};