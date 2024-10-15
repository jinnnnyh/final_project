import {useState} from "react";

const ApprovalButton = () => {
  const [isActive, setIsActive] = useState('Y');
  const [loading, setLoading] = useState('Y');

  const handleClick = async () => {
    setLoading('N');

    try {
      // DB에 상태를 저장
      const response = await fetch('http://localhost:8080/user/userManage', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ active: !isActive }),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      // 상태 변경
      setIsActive((prev) => !prev);
    } catch (error) {
      console.error('Error saving to DB:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button onClick={handleClick} disabled={loading}>
      {loading ? 'Loading...' : (isActive ? '승인대기' : '승인완료')}
    </button>
  );
};

export default ApprovalButton;