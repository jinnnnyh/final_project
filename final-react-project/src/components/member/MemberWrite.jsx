import Member from "../../pages/Member.jsx";

function MemberList () {

  return (
    <section>
      <Member/>
      <div className={'col-10'}>
        <form className={'form-border'}>
          <div className={'d-flex mt-4 justify-content-between'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'name'} className={'form-label'}>이름</label>
              <input type={'text'} className={'form-control py-3'} placeholder={'이름을 입력해주세요'} id={'name'}/>
            </div>
            <div className={'w-50'}>
              <label htmlFor={'tel'} className={'form-label'}>전화번호</label>
              <input type={'text'} className={'form-control py-3'} placeholder={'전화번호를 입력해주세요'} id={'tel'}/>
            </div>
          </div>

          <div className="mt-4">
            <label htmlFor={'email'} className={'form-label'}>이메일</label>
            <input type={'text'} className={'form-control py-3'} placeholder={'이메일을 입력해주세요'} id={'email'}/>
          </div>

          <div className={'d-flex mt-4'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'company'} className={'form-label'}>회사명</label>
              <input type={'text'} className={'form-control py-3'} placeholder={'회사명을 입력해주세요'} id={'company'}/>
            </div>
            <div className={'w-50 '}>
              <label htmlFor={'job'} className={'form-label'}>직위</label>
              <input type={'text'} className={'form-control py-3'} id={'job'}/>
            </div>
          </div>

          {/* 버튼 */}
          <div className={'d-flex justify-content-end mt-5'}>
              <button type={'submit'} className={'btn btn-outline-point me-2'}>취소</button>
              <button type={'submit'} className={'btn btn-point'}>완료</button>
          </div>
        </form>
      </div>
    </section>
  )
}

export default MemberList;