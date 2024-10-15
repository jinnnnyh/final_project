const Confirm = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="modal-overlay">
      <div className="modal">
        <p>{message}</p>
        <button onClick={onConfirm}>승인</button>
        <button onClick={onCancel}>거부</button>
      </div>
    </div>
  );
};

export default Confirm;