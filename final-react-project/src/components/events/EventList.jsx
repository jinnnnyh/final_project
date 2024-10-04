import Events from "../../pages/Events.jsx";

function EventList () {
  return (
    <section>
      <Events/>
      <div className={'col-10 d-flex justify-content-between'}>
        <div className={'w-50 boxLayout'}>
          <button className={'btn btn-outline-danger'}>승인예정</button>
          <img src="/noimg.png" alt="noImg" className={'w-100 my-4'}/>
          <h4>행사제목</h4>
          <p>행사기간 : <span>2024.10.01</span></p>
          <button type={'button'} className={'btn btn-point'}>참석자 리스트</button>
        </div>
        <div className={'bg-light w-50'}>fffffff</div>
      </div>
    </section>
  )
}

export default EventList;