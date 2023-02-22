import React, { useState } from "react";
import CsvUploader from './components/CsvUploader';
import EmployeeList from './components/EmployeeList';

const UPLOADED_KEY = "employees_uploaded"

function App() {
  const [employeesUploaded, setEmployeesUploaded] = useState(
    JSON.parse(localStorage.getItem(UPLOADED_KEY)) || false
  );
  const [refresh, setRefresh] = useState(false);

  const handleUploadSuccess = () => {
      setEmployeesUploaded(true);
      localStorage.setItem(UPLOADED_KEY, true);
      setRefresh(!refresh);
  };

  return (
    <div className="App">
      <CsvUploader onUploadSuccess={handleUploadSuccess}/>

      <EmployeeList empReady={employeesUploaded} refresh={refresh}/>
    </div>
  );
}

export default App;
