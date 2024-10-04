import axios from "axios";
import {useEffect, useState} from "react";
import TempItems from "./TempItems.jsx";

function tempComponent() {

  const [tempData, setTempData] = useState();

  useEffect(() => {
    axios.get('http://localhost:8080/temp')
      .then(res => {
        setTempData(res.data);
        //console.log(tempData);
      })
      .catch(err => {
        alert("통신 실패." + err);
      });
  }, [tempData]);

  return (
    <div className={'container mt-5'}>
      <h1>TempComponent</h1>

      {tempData?.map(item => {
        return <TempItems key={item.id} data={item} />
      })}

    </div>
  );
}

export default tempComponent;