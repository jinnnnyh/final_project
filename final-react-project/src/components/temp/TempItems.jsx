
function TempItems(props) {
  return (
    <div>
      <h3>id = {props.data.id}</h3>
      <h3>createdDate = {props.data.createdDate}</h3>
      <h3>updatedDate = {props.data.updatedDate}</h3>
    </div>
  );
}

export default TempItems;