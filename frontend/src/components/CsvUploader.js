import React, { useState } from "react";

function CsvUploader({onUploadSuccess}) {
    const [uploadSuccess, setUploadSuccess] = useState(null);
    const [uploadError, setUploadError] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);

    function handleFileSelected(file) {
        setSelectedFile(file);
    }

    function handleFileUpload() {
        if (!selectedFile) {
            setUploadSuccess(false);
            setUploadError("No file selected");
            return;
        }

        const formData = new FormData();
        formData.append("employees", selectedFile);
        
        fetch("/api/employees/upload", {
            method: "POST",
            body: formData
        })
        .then((response) => {
            if (response.ok) {
                setUploadSuccess(true);
                setUploadError(false);
                onUploadSuccess();                
            } else {
                setUploadSuccess(false);
                response.text().then((errorMessage) => {
                    setUploadError(errorMessage);
                });
            }
        })
        .catch((error) => {
            setUploadSuccess(false);
            setUploadError(error.message);
        });
        setSelectedFile(null);
        document.getElementById("file-input").value = null;
    };

    const renderUploadStatus = () => {
        if (uploadSuccess === true) {
            return <div id="status-text">Upload successful!</div>
        } else if (uploadSuccess === false) {
            return <div id="status-text">{uploadError}</div>
        } else {
            return null;   
        }
    };

    return (
        <>
        <div className="file-upload">
            <input type="file" id="file-input" accept=".csv" className="hidden" onChange={(e) => handleFileSelected(e.target.files[0])} />
            <label htmlFor="file-input" className="file-label">Select file</label>
            {selectedFile && (
                <p> Selected file: {selectedFile.name}</p>
            )}
            <button onClick={handleFileUpload} className="submit-button">Upload</button>        
        </div>
        <div className={"status " + (uploadSuccess ? "success" : "error")}>
            {renderUploadStatus()}
        </div>
        </>
    );
}

export default CsvUploader;